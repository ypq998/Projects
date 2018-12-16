package com.apple.account.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apple.account.data.entity.Expense;
import com.apple.account.data.entity.TotalInfo;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
//	@Query(value = "select *,create_time as createTime from v_expense where name like %?1%", countQuery = "select count(*) from v_expense  where name like %?1%", nativeQuery = true)
//	Page<Expense> findByNameLike(String name, Pageable pageable);

	@Query(value = "select * from expense where item_id =?1 order by create_time desc limit 1", nativeQuery = true)
	Expense findByItemID(long itemID);
	
	/**
	 * 
	 * @return
	 */
	@Query(value = "select DATE_FORMAT(CREATE_TIME,'%Y') as catalog,sum(charge) as charge from v_expense group by DATE_FORMAT(CREATE_TIME,'%Y') order by DATE_FORMAT(CREATE_TIME,'%Y') asc ", nativeQuery = true)
	List getTotalByYear();
	
	/**
	 * 
	 * @return
	 */
	@Query(value = "select name as catalog,sum(charge) as charge from v_expense group by name order by sum(charge) desc limit 10", nativeQuery = true)
	List getTotalByItem();
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	@Query(value = "select DATE_FORMAT(CREATE_TIME,'%m') as catalog,sum(charge) as charge from v_expense where DATE_FORMAT(CREATE_TIME,'%Y')=?1 group by DATE_FORMAT(CREATE_TIME,'%Y-%m') order by DATE_FORMAT(CREATE_TIME,'%Y-%m') asc", nativeQuery = true)
	List getTotalThisYearByMonth(int year);
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	@Query(value = "select name as catalog,sum(charge) as charge from v_expense where DATE_FORMAT(CREATE_TIME,'%Y')=?1 group by name order by sum(charge) desc limit 10", nativeQuery = true)
	List getAllTotalThisYearByItem(int year);
	
}
