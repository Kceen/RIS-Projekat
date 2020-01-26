package ris.projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	public List<Image> findByItem_idItem(int Item_idItem);
}
