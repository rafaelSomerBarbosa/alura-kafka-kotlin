import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.io.Closeable
import java.time.Duration
import java.util.*
import java.util.regex.Pattern
import kotlin.reflect.KFunction1

class KafkaService(
        private val simpleName: String,
        private val topic: String?,
        private val topicPattern: Pattern?,
        private val parse: KFunction1<ConsumerRecord<String, String>, Unit>
) : Closeable {
    private var kafkaConsumer = KafkaConsumer<String, String>(properties())

    fun run() {
        if (topic != null) kafkaConsumer.subscribe(listOf(topic))
        if (topicPattern != null) kafkaConsumer.subscribe(topicPattern)

        while (true) {
            val records = kafkaConsumer.poll(Duration.ofMillis(100))
            if(!records.isEmpty) {
                for(record in records) {
                    parse(record)
                    try {
                        Thread.sleep(1000)
                    } catch(e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    private fun properties() : Properties {
        val properties = Properties()
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, simpleName)
        return properties
    }

    override fun close() {
        kafkaConsumer.close()
    }
}