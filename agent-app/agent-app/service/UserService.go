package service

import (
	"agent-app/auth0"
	"agent-app/dto"
	"agent-app/mapper"
	"agent-app/repository"
	"errors"
	"fmt"
	"regexp"
	"strings"

	"golang.org/x/crypto/bcrypt"
)

type UserService struct {
	UserRepo    repository.IUserRepository
	Auth0Client auth0.Auth0Client
}

type IUserService interface {
	Register(*dto.RegistrationRequestDTO) (int, error)
	GetByEmail(string) (*dto.UserResponseDTO, error)
}

func NewUserService(userRepository repository.IUserRepository, auth0Client auth0.Auth0Client) IUserService {
	return &UserService{
		userRepository,
		auth0Client,
	}
}

func (service *UserService) Register(userToRegister *dto.RegistrationRequestDTO) (int, error) {
	if strings.TrimSpace(userToRegister.Password) == "" || len(userToRegister.Password) < 8 {
		return -1, errors.New("Password must be at least 8 characters long!")
	}
	if match, _ := regexp.MatchString(".*\\d.*", userToRegister.Password); !match {
		return -1, errors.New("Password must contain at least one number!")
	}

	user := mapper.RegistrationRequestDTOToUser(userToRegister)

	err := user.Validate()
	if err != nil {
		return -1, err
	}

	user.Password, err = HashPassword(user.Password)
	if err != nil {
		fmt.Println(err)
		return -1, err
	}

	addedUserID, err := service.UserRepo.AddUser(user)
	if err != nil {
		fmt.Println(err)
		return -1, err
	}

	if auth0ID, err := service.Auth0Client.Register(userToRegister.Email, userToRegister.Password); err != nil {
		fmt.Println(err)
		if err = service.UserRepo.DeleteUser(addedUserID); err != nil {
			return -1, err
		}
		return -1, err
	} else {
		user.Auth0ID = auth0ID
		service.UserRepo.Update(user)
	}

	return addedUserID, nil
}

func HashPassword(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
	return string(bytes), err
}

func (service *UserService) GetByEmail(email string) (*dto.UserResponseDTO, error) {
	return service.UserRepo.GetByEmail(email)
}
