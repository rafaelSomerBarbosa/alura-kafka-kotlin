import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

class EmailService {
    fun main() {
        val emailService = EmailService()
        val kafkaService = KafkaService(EmailService::class.java.simpleName, "ECOMMERCE_SEND_EMAIL", emailService::parse)
        kafkaService.run()
    }

    private fun parse(record: ConsumerRecord<String, String>) {
        println("E-mail enviado | Chave ${record.key()} | Value ${record.value()} | Partition ${record.partition()} | Topic ${record.topic()} | ")
    }

    private fun properties() : Properties {
        val properties = Properties()
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService::class.java.simpleName)
        return properties
    }
}