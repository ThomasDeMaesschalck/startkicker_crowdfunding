package be.hogent.startkicker.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.hogent.startkicker.business.User;
import be.hogent.startkicker.business.repositories.IUserRepo;
import be.hogent.startkicker.business.repositories.UserJPARepo;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.mappers.IMapper;
import be.hogent.startkicker.service.mappers.UserMapper;

/**
 * User service for communication with frontend
 */
public class UserService {

	/**
	 * The User repository
	 */
	private IUserRepo userRepo;

	/**
	 * Mapper to map User to UserDTO and the other way around
	 */
	private IMapper<User, UserDTO> userMapper;

	/**
	 * The UserService instance
	 */
	private static UserService instance;

	/**
	 * Singleton pattern
	 * @return Returns the UserService instance
	 */
	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	/**
	 * Default private constructor for the singleton pattern
	 */
	private UserService() {
		userRepo = new UserJPARepo();
		userMapper = new UserMapper();
	}

	/**
	 * Save user to database
	 * @param uDTO UserDTO that needs to be persisted
	 * @return String with success or error code
	 */
	public String saveUser(UserDTO uDTO) {
		return userRepo.saveUser(userMapper.mapDTOToObject(uDTO));
	}

	/**
	 * Get a user from the DB based on user id
	 * @param id Long that represents the id of the user in the database
	 * @return The UserDTO of the requested user
	 */
	public UserDTO getUser(long id)
	{
		return userMapper.mapObjectToDTO(userRepo.getUser(id));
	}

	/**
	 * Delete a user from the DB
	 * @param uDTO UserDTO of user that needs to be deleted
	 * @return A String with success or error code
	 */
	public String deleteUser(UserDTO uDTO)
	{
		return userRepo.deleteUser(userMapper.mapDTOToObject(uDTO));
	}

	/**
	 * Retrieve a list of all users
	 * @return List of UserDTOs
	 */
	public List<UserDTO> getAllUsers() {
		return userMapper.allObjectToDTO(userRepo.getAllUsers());

	}

	/**
	 * Allows an admin to switch a user status from inactive to active
	 * @param idUser User id of the user that needs to be activated
	 */
	public void switchUserActive(long idUser) {
		UserDTO p = userMapper.mapObjectToDTO(userRepo.getUser(idUser));
		p.setActif(!p.isActif());
		userRepo.saveUser(userMapper.mapDTOToObject(p));
	}

	/**
	 * Calculate the total amount of money a logged in user has pledged to projects
	 * @param user The logged in user
	 * @param fundedProjectList  The list that needs calculation
	 * @return BigDecimal of how much money the user has pledged to projects
	 */
	public BigDecimal userTotalFunded(UserDTO user, List<ProjectDTO> fundedProjectList)
	{
		List<BigDecimal> values = new ArrayList<>();
		for (ProjectDTO project: fundedProjectList) {
			values.addAll(project.getFunding().stream().filter(f -> f.getUser().getId() == user.getId()).map(FundingDTO::getAmount).collect(Collectors.toList()));
		}
		BigDecimal amount = values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		return amount;
	}

}
