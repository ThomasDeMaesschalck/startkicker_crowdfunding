package be.hogent.startkicker.service;

import java.math.BigDecimal;
import java.util.List;

import be.hogent.startkicker.business.User;
import be.hogent.startkicker.business.repositories.IUserRepo;
import be.hogent.startkicker.business.repositories.UserJPARepo;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.mappers.IMapper;
import be.hogent.startkicker.service.mappers.UserMapper;

public class UserService {

	private IUserRepo userRepo;
	private IMapper<User, UserDTO> userMapper;
	
	private static UserService instance;

	

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	private UserService() {
		userRepo = new UserJPARepo();
		userMapper = new UserMapper();
	}

	public String saveUser(UserDTO uDTO) {
		return userRepo.saveUser(userMapper.mapDTOToObject(uDTO));
	}

	public UserDTO getUser(long id)
	{
		return userMapper.mapObjectToDTO(userRepo.getUser(id));
	}

	public User getUserById(long id)
	{
		return userMapper.mapDTOToObject(getUser(id));
	}

	public String deleteUser(UserDTO uDTO)
	{
		return userRepo.deleteUser(userMapper.mapDTOToObject(uDTO));
	}

	public List<UserDTO> getAllUsers() {
		return userMapper.allObjectToDTO(userRepo.getAllUsers());

	}

	public void switchUserActive(long idUser) {
		UserDTO p = userMapper.mapObjectToDTO(userRepo.getUser(idUser));
		p.setActif(!p.isActif());
		userRepo.saveUser(userMapper.mapDTOToObject(p));

	}

	public BigDecimal userTotalFunded(UserDTO user)
	{
		BigDecimal amount = null;
		List<ProjectDTO> fundedProjectList = ProjectService.getInstance().getAllProjectsFundedByUser(user);
		for (ProjectDTO p: fundedProjectList)
		{
			amount.add(p.getFunding().stream().map(FundingDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
		}
		return amount;
	}

}
