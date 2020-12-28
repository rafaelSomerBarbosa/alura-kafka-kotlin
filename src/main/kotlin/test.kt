import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    launch(Dispatchers.Default) {
        FraudDetectorService().main()
    }

    launch(Dispatchers.Default) {
        FraudDetectorService().main()
    }

    launch(Dispatchers.Default) {
        FraudDetectorService().main()
    }

    launch(Dispatchers.Default) {
        FraudDetectorService().main()
    }

    launch(Dispatchers.Default) {
        EmailService().main()
    }

    launch(Dispatchers.Default) {
        LogService().main()
    }
}