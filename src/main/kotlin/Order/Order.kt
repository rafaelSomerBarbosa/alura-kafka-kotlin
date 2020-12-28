package Order

import java.math.BigDecimal

class Order(
        val userId: String,
        val orderId: String,
        val amount: BigDecimal
)
