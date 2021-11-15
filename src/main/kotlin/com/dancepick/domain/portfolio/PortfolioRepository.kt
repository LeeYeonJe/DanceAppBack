package com.dancepick.domain.portfolio

import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioRepository : JpaRepository<Portfolio,Long> {
}