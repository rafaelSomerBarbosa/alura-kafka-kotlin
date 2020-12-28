package ConsumerFunction

import org.apache.kafka.clients.consumer.ConsumerRecord

interface ConsumerFunction {
    fun consume(record: ConsumerRecord<String, String>)
}
