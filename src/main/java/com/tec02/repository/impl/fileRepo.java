package com.tec02.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;
import com.tec02.repository.IBaseRepo;

public interface fileRepo extends IBaseRepo<File> {

	Optional<File> findOneByFileGroupAndNameAndPath(FileGroup fileGroup, String fileName, String fileDir);

	List<File> findAllByFileGroupId(Long id);

	Optional<File> findOneByIdAndFileGroupId(Long fileId, Long fgroundID);

	Optional<File> findOneByFileGroupIdAndNameAndPath(Long fgId, String fileName, String filePath);
	
	@Query("select e.id from File e where e.fileGroup.id = ?1")
	List<Long> findAllIdByFileGroupId(Long id);

}
