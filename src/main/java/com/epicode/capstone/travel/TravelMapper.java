package com.epicode.capstone.travel;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TravelMapper {
    TravelMapper INSTANCE = Mappers.getMapper(TravelMapper.class);

    CompleteResponse travelToResponse(Travel travel);
    List<CompleteResponse> travelsToResponses(List<Travel> travels);
}
