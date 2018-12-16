package com.apple.account.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apple.account.data.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
//	@Query(value = "select t  from expense t where m.name like '%?1%'")
//	List<Expense> findByNameLike(String name);
}
