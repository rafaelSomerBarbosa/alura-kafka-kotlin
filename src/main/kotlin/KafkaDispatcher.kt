import GsonSerializer.GsonSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.io.Closeable
import java.util.*

class KafkaDispatcher<T> : Closeable {
    private val producer = KafkaProducer<String, T>(properties())

    fun send(topic: String, key: String, value: T) {
        val record = ProducerRecord(topic, key, value)

        producer.send(record) { data, ex ->
            if (ex != null) {
                ex.printStackTrace()
                return@send
            }
            println("${data.topic()} :::partition ${data.partition()} :::offset ${data.offset()} :::timestamp ${data.timestamp()}")
        }.get()
    }

    private fun properties() : Properties {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer::class.java.name)
        return properties
    }

    override fun close() {
        producer.close()
    }
}