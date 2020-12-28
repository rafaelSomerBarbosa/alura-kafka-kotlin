import org.apache.kafka.clients.consumer.ConsumerRecord
import java.util.regex.Pattern

class LogService {
    fun main() {
        val logService = LogService()
        val kafkaService = KafkaService(LogService::class.java.simpleName, topic = null, topicPattern = Pattern.compile("ECOMMERCE.*"), parse = logService::parse)
        kafkaService.run()
    }

    private fun parse(record: ConsumerRecord<String, String>) {
        println("LOG, ${record.topic()} | ${record.key()} | ${record.value()} | ${record.partition()} | ${record.offset()}")
    }
}