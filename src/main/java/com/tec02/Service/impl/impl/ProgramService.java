package com.tec02.Service.impl.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec02.Service.impl.BaseService;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.impl.impl.impl.impl.impl.ProgramDto;
import com.tec02.model.dto.updownload.impl.FileInfomationDto;
import com.tec02.model.dto.updownload.impl.impl.AppInfomationDto;
import com.tec02.model.entity.impl.Location;
import com.tec02.model.entity.impl.Version;
import com.tec02.model.entity.impl.VersionProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.File;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileGroup;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.FileProgram;
import com.tec02.model.entity.impl.modifiableEnityimpl.haveLocationImpl.haveDiscriptionimpl.Program;
import com.tec02.repository.IBaseRepo;
import com.tec02.repository.impl.ProgramRepo;
import com.tec02.util.ModelMapperUtil;

@Service
public class ProgramService extends BaseService<ProgramDto, Program> {

	@Autowired
	private ProgramRepo programRepo;

	@Autowired
	private FGroupService groupService;

	@Autowired
	private FileProgramService fileProgramService;

	protected ProgramService(IBaseRepo<Program> repository) {
		super(repository, ProgramDto.class, Program.class);
	}

	public List<ProgramDto> findAllByLocation(List<Location> locations, String name) {
		name = String.format("%%%s%%", name == null ? "" : name);
		return convertToDtos(this.programRepo.findAllByLocationIn(locations, name));
	}

	public ProgramDto addFileGroup(Long id, RequestDto requestDto) {
		id = id == null ? requestDto.getId() : id;
		Program program = this.findOne(id);
		List<FileGroup> fileGroups = this.groupService.findAllByIds(requestDto.getIds());
		if (fileGroups == null || fileGroups.isEmpty()) {
			return convertToDto(program);
		} else {
			program.addAllGroupFile(fileGroups);
		}
		return convertToDto(this.programRepo.save(program));
	}

	public ProgramDto addFileProgram(Long id, Long fileProgramID) {
		Program program = this.findOne(id);
		FileProgram fileGroups = this.fileProgramService.findOne(fileProgramID);
		if (fileGroups == null) {
			return convertToDto(program);
		} else {
			program.setFileProgram(fileGroups);
		}
		return convertToDto(this.programRepo.save(program));
	}

	public ProgramDto removeFileGroup(Long id, RequestDto requestDto) {
		id = id == null ? requestDto.getId() : id;
		Program program = this.findOne(id);
		program.removeAllGroupFileIds(requestDto.getIds());
		return convertToDto(this.programRepo.save(program));
	}

	public Map<Long, Map<Long, FileInfomationDto>> getAllProgramsFileByIds(Collection<Long> ids) {
		Map<Long, Map<Long, FileInfomationDto>> maps = new HashMap<>();
		for (Long id : ids) {
			maps.put(id, getAllProgramsFileById(id));
		}
		return maps;
	}

	public List<AppInfomationDto> getAllPrograms(Collection<Program> programs) {
		List<AppInfomationDto> apps = new ArrayList<>();
		AppInfomationDto app;
		for (Program program : programs) {
			app = ModelMapperUtil.map(program, AppInfomationDto.class);
			FileProgram fileProgram = program.getFileProgram();
			if (!program.isEnable() || fileProgram == null) {
				continue;
			}
			for (VersionProgram version : fileProgram.getVersions()) {
				if (version != null && version.isEnable()) {
					FileInfomationDto fi = new FileInfomationDto();
					fi.setId(fileProgram.getId());
					fi.setFilename(fileProgram.getName());
					fi.setFilepath(
							Path.of(String.format("%s_%s", program.getId(), program.getName()), fileProgram.getPath())
									.toString());
					fi.setVersion(version.getName());
					fi.setMd5(version.getMd5());
					fi.setDesciption(version.getDescription());
					app.setFileProgram(fi);
					break;
				}
			}
			app.setFiles(getAllProgramsFileById(program.getId()));
			if (app.isEnable() && !app.getFiles().isEmpty()) {
				apps.add(app);
			}
		}
		return apps;
	}

	public Map<Long, FileInfomationDto> getAllProgramsFileById(Long id) {
		Program program = this.findOne(id);
		if (program == null) {
			throw new RuntimeException(String.format("program id not existe!, id = %s", id));
		}
		Map<Long, FileInfomationDto> versions = new HashMap<>();
		for (FileGroup fileGroup : program.getGroupFiles()) {
			for (File file : fileGroup.getFiles()) {
				for (Version version : file.getVersions()) {
					if (version != null && version.isEnable()) {
						FileInfomationDto fi = new FileInfomationDto();
						fi.setId(file.getId());
						fi.setFilename(file.getName());
						fi.setFilepath(
								Path.of(String.format("%s_%s", program.getId(), program.getName()), file.getPath())
										.toString());
						fi.setVersion(version.getName());
						fi.setMd5(version.getMd5());
						fi.setDesciption(version.getDescription());
						versions.put(fi.getId(), fi);
						break;
					}
				}
			}
		}
		return versions;
	}

	public List<AppInfomationDto> getAllProgramsFileByLocations(List<Location> locations) {
		Set<Program> programs = new HashSet<>();
		for (Location location : locations) {
			programs.addAll(location.getPrograms());
		}
		return getAllPrograms(programs);
	}

}
