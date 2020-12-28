import org.apache.kafka.clients.consumer.ConsumerRecord

class FraudDetectorService {
    fun main() {
        val fraudDetectorService = FraudDetectorService()
        val kafkaService = KafkaService(FraudDetectorService::class.java.simpleName, "ECOMMERCE_NEW_ORDER", fraudDetectorService::parse)
        kafkaService.run()
    }

    private fun parse(record: ConsumerRecord<String, String>) {
        println("Processando new order, checking from fraud ${record.key()} | ${record.value()} | ${record.partition()} | ${record.offset()}")
    }
}