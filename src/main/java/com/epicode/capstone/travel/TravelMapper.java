package com.epicode.capstone.travel;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TravelMapper {
    TravelMapper INSTANCE = Mappers.getMapper(TravelMapper.class);

    CompleteResponse travelToCompleteResponse(Travel travel);
    List<CompleteResponse> travelsToCompleteResponses(List<Travel> travels);

    Response travelToResponse(Travel travel);
    List<Response> travelsToResponses(List<Travel> travels);
}
