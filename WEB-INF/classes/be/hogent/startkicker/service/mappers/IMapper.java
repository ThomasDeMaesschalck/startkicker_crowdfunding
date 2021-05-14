package be.hogent.startkicker.service.mappers;

import java.util.List;

/**
 * Interface for the service layer mappers. Mapping Object to DTO and other way around.
 * @param <O> Object
 * @param <DTO> DTO
 */
public interface IMapper<O, DTO> {

	/**
	 * Map a DTO to Object
	 * @param d DTO
	 * @return Object
	 */
	public O mapDTOToObject(DTO d);

	/**
	 * Map Object to DTO
	 * @param o Object
	 * @return DTO
	 */
	public DTO mapObjectToDTO(O o);

	/**
	 * Map a list of DTOs to Objects
	 * @param d List of DTO
	 * @return List of Object
	 */
	public List<O> allDTOToObject(List<DTO> d);

	/**
	 * Map a list of objects to DTOs
	 * @param o List of Objects
	 * @return List of DTOs
	 */
	public List<DTO> allObjectToDTO(List<O> o);

}
