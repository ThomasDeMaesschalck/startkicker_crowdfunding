package be.hogent.startkicker.service.mappers;

import javax.validation.Valid;
import java.util.List;

public interface IMapper<O, DTO> {

	public O mapDTOToObject(DTO d);

	public DTO mapObjectToDTO(O o);

	public List<O> allDTOToObject(List<DTO> d);

	public List<DTO> allObjectToDTO(List<O> o);

}
