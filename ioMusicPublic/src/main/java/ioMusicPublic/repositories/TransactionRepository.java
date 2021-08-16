package ioMusicPublic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ioMusicPublic.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
