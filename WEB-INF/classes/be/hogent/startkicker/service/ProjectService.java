package be.hogent.startkicker.service;

import be.hogent.startkicker.business.Project;
import be.hogent.startkicker.business.User;
import be.hogent.startkicker.business.repositories.IProjectRepo;
import be.hogent.startkicker.business.repositories.IUserRepo;
import be.hogent.startkicker.business.repositories.ProjectJPARepo;
import be.hogent.startkicker.business.repositories.UserJPARepo;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;
import be.hogent.startkicker.service.mappers.IMapper;
import be.hogent.startkicker.service.mappers.ProjectMapper;
import be.hogent.startkicker.service.mappers.UserMapper;

import java.util.List;

public class ProjectService {
        private IProjectRepo projectRepo;
        private IMapper<Project, ProjectDTO> projectMapper;

        private static ProjectService instance;


        public static ProjectService getInstance() {
            if (instance == null) {
                instance = new ProjectService();
            }
            return instance;
        }

        private ProjectService() {
            projectRepo = new ProjectJPARepo();
            projectMapper = new ProjectMapper();
        }

        public String saveProject(ProjectDTO pDTO) {
            return projectRepo.saveProject(projectMapper.mapDTOToObject(pDTO));
        }

        public String deleteProject(ProjectDTO pDTO)
        {
            return projectRepo.deleteProject(projectMapper.mapDTOToObject(pDTO));
        }

        public List<ProjectDTO> getAllProjects() {
            return projectMapper.allObjectToDTO(projectRepo.getAllProjects());

        }


}
