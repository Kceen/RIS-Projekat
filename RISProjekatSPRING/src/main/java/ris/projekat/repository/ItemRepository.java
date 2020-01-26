package ris.projekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
