package com.exalt.managment.managementsystem.mapper;

import com.exalt.managment.managementsystem.model.dao.ServerDAO;
import com.exalt.managment.managementsystem.model.dto.ServerDTO;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The interface Server mapper is a mapStruct interface to map ServerDAO to ServerDTO
 */
@Mapper
public interface ServerMapper {

  ServerMapper INSTANCE = Mappers.getMapper(ServerMapper.class);

  ServerDTO serverDaoToDto(ServerDAO server);

  ArrayList<ServerDTO> serverListToDto(List<ServerDAO> server);
}

