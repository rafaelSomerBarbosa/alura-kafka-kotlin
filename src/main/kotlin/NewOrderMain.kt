import Order.Order
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import java.math.BigDecimal
import java.util.*

class NewOrderMain {
    fun main(args: Array<String>) {
        val orderDispatcher = KafkaDispatcher<Order>()
        val emailDispatcher = KafkaDispatcher<String>()

        for(i in 1..10) {
            val userId = UUID.randomUUID().toString()
            val orderId = UUID.randomUUID().toString()
            val amount = BigDecimal(Math.random() * 5000 + 1)

            val order = Order(userId, orderId, amount)

            val keyEmail = UUID.randomUUID().toString()
            val email = "${keyEmail} - Welcome! We are processing your order!"


            orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order)
            emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, email)
        }
    }

    private fun properties() : Properties {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        return properties
    }
}