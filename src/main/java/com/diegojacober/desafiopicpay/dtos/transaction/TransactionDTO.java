package com.diegojacober.desafiopicpay.dtos.transaction;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {

}
