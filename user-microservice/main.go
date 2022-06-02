package main

import (
	"log"
	"net"
	"net/http"
	"os"
	"user-ms/auth0"
	"user-ms/handler"
	"user-ms/model"
	"user-ms/repository"
	"user-ms/service"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"github.com/jelenac11/SharedProba/jwt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/joho/godotenv"
	"github.com/soheilhy/cmux"
)

var db *gorm.DB
var err error

func initDB() *gorm.DB {

	db, err = gorm.Open("postgres", "host=localhost port=5432 user=postgres dbname=DevOps-Users sslmode=disable password=zovemsejelenajelena")

	if err != nil {
		panic("failed to connect database")
	}

	db.AutoMigrate(model.User{})

	return db
}

func initUserRepo(database *gorm.DB) *repository.UserRepository {
	return &repository.UserRepository{Database: database}
}

func initAuth0Client() *auth0.Auth0Client {
	domain := os.Getenv("AUTH0_DOMAIN")
	clientId := os.Getenv("AUTH0_CLIENT_ID")
	clientSecret := os.Getenv("AUTH0_CLIENT_SECRET")
	audience := os.Getenv("AUTH0_AUDIENCE")

	client := auth0.NewAuth0Client(domain, clientId, clientSecret, audience)
	return &client
}

func initUserService(userRepo *repository.UserRepository, auth0Client *auth0.Auth0Client) *service.UserService {
	return &service.UserService{UserRepo: userRepo, Auth0Client: *auth0Client}
}

func initUserHandler(service *service.UserService) *handler.UserHandler {
	return &handler.UserHandler{Service: service}
}

func handleUserFunc(handler *handler.UserHandler, router *gin.Engine) {
	router.POST("/register", handler.Register)
	router.GET("/register2", jwt.GetJwtMiddleware(), jwt.CheckRoles([]string{"read:raed"}), handler.Register2)
	router.GET("/users", handler.GetByEmail)
	router.PUT("/users", handler.Update)
}

func main() {

	err := godotenv.Load(".env")

	if err != nil {
		log.Fatal("Error loading .env file")
	}

	database := initDB()

	l, err := net.Listen("tcp", ":9093")
	if err != nil {
		panic(err)
	}

	m := cmux.New(l)

	httpListener := m.Match(cmux.HTTP1Fast())

	userRepo := initUserRepo(database)
	auth0Client := initAuth0Client()
	userService := initUserService(userRepo, auth0Client)
	userHandler := initUserHandler(userService)

	router := gin.Default()
	corsConfig := cors.DefaultConfig()
	corsConfig.AllowAllOrigins = true
	corsConfig.AddAllowHeaders("Authorization")
	router.Use(cors.New(corsConfig))

	handleUserFunc(userHandler, router)

	httpS := &http.Server{
		Handler: router,
	}

	go httpS.Serve(httpListener)

	log.Printf("Running http on port 9093")
	m.Serve()
}
