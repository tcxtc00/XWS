package rabbitmq

import (
	"encoding/json"
	"posts-ms/src/dto/response"
	"time"

	"github.com/gofrs/uuid"
	"github.com/streadway/amqp"
)

type RMQProducer struct {
	ConnectionString string
}

func (r RMQProducer) StartRabbitMQ() (*amqp.Channel, error) {
	connectRabbitMQ, err := amqp.Dial(r.ConnectionString)

	if err != nil {
		return nil, err
	}

	channelRabbitMQ, err := connectRabbitMQ.Channel()

	if err != nil {
		return nil, err
	}

	return channelRabbitMQ, err
}

func DeleteImage(id uint, channel *amqp.Channel) {
	uuid, _ := uuid.NewV4()

	media := response.MediaDto{
		Id:  id,
		Url: "",
	}

	payload, _ := json.Marshal(media)

	channel.Publish(
		"DeleteImageOnMedias-MS-exchange",    // exchange
		"DeleteImageOnMedias-MS-routing-key", // routing key
		false,                                // mandatory
		false,                                // immediate
		amqp.Publishing{
			ContentType:  "application/json",
			DeliveryMode: amqp.Persistent,
			MessageId:    uuid.String(),
			Timestamp:    time.Now(),
			Body:         payload,
		})
}
