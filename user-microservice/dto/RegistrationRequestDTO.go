package dto

import "user-ms/model"

type RegistrationRequestDTO struct {
	Username    string
	FirstName   string
	LastName    string
	DateOfBirth float32
	Email       string
	PhoneNumber string
	Gender      *model.Gender
	Password    string
}
