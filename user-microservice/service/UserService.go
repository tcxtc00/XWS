package service

import (
	"errors"
	"fmt"
	"regexp"
	"strings"
	"user-ms/auth0"
	"user-ms/dto"
	"user-ms/mapper"
	"user-ms/repository"

	"golang.org/x/crypto/bcrypt"
)

type UserService struct {
	UserRepo    repository.IUserRepository
	Auth0Client auth0.Auth0Client
}

type IUserService interface {
	Register(*dto.RegistrationRequestDTO) (int, error)
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

	if _, err := service.Auth0Client.Register(userToRegister.Email, userToRegister.Password); err != nil {
		fmt.Println(err)
		if err = service.UserRepo.DeleteUser(addedUserID); err != nil {
			return -1, err
		}
		return -1, err
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

func (service *UserService) Update(userToUpdate *dto.UserUpdateDTO) (*dto.UserResponseDTO, error) {
	user := mapper.UserUpdateDTOToUser(userToUpdate)

	err := user.Validate()
	if err != nil {
		return nil, err
	}

	userDTO, err := service.UserRepo.Update(user)
	if err != nil {
		fmt.Println(err)
		return nil, err
	}

	// if _, err := service.Auth0Client.Register(userToRegister.Email, userToRegister.Password); err != nil {
	// 	fmt.Println(err)
	// 	if err = service.UserRepo.DeleteUser(addedUserID); err != nil {
	// 		return -1, err
	// 	}
	// 	return -1, err
	// }

	return userDTO, nil
}
