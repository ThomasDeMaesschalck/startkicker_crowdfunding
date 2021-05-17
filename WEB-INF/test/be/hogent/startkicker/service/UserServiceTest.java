package be.hogent.startkicker.service;

import be.hogent.startkicker.business.ProjectStatus;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {


    private UserDTO testUser;
    private UserDTO testUser2;
    private ProjectDTO testProjectDTO;
    private ProjectDTO testProjectDTO2;
    private FundingDTO funding1;
    private FundingDTO funding2;
    private FundingDTO funding3;
    private Set<FundingDTO> testSet;
    private Set<FundingDTO> testSet2;
    private List<ProjectDTO> projectDTOList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        testUser = new UserDTO();
        testUser.setId(1);
        testUser.setName("TestUser");
        testUser.setFirstName("Thomas");

        testUser2 = new UserDTO();
        testUser2.setId(2);
        testUser2.setName("TestUser2");
        testUser2.setFirstName("Karel");


        LocalDate startDate = LocalDate.of(3000, 1, 8);
        LocalDate endDate = LocalDate.now().plusDays(10);

        BigDecimal fundingTarget = BigDecimal.valueOf(5000);

        testProjectDTO = new ProjectDTO();
        testProjectDTO.setId(1);
        testProjectDTO.setTitle("test title");
        testProjectDTO.setDescription("test description");
        testProjectDTO.setCreator(testUser);
        testProjectDTO.setStartDate(startDate);
        testProjectDTO.setEndDate(endDate);
        testProjectDTO.setStatus(ProjectStatus.Created);
        testProjectDTO.setFundingTarget(fundingTarget);

        testProjectDTO2 = new ProjectDTO();
        testProjectDTO2.setId(1);
        testProjectDTO2.setTitle("test title");
        testProjectDTO2.setDescription("test description");
        testProjectDTO2.setCreator(testUser);
        testProjectDTO2.setStartDate(startDate);
        testProjectDTO2.setEndDate(endDate);
        testProjectDTO2.setStatus(ProjectStatus.Created);
        testProjectDTO2.setFundingTarget(fundingTarget);


        funding1 = new FundingDTO();
        funding1.setId(1);
        funding1.setAmount(BigDecimal.valueOf(500));
        funding1.setUser(testUser);
        funding1.setProject(testProjectDTO);

        funding2 = new FundingDTO();
        funding2.setId(2);
        funding2.setAmount(BigDecimal.valueOf(500));
        funding2.setUser(testUser);
        funding2.setProject(testProjectDTO2);

        funding3 = new FundingDTO();
        funding3.setId(3);
        funding3.setAmount(BigDecimal.valueOf(400));
        funding3.setUser(testUser2);
        funding3.setProject(testProjectDTO);

        testSet = new HashSet<>();
        testSet.add(funding1);
        testSet.add(funding3);

        testSet2 = new HashSet<>();
        testSet2.add(funding2);

        testProjectDTO.setFunding(testSet);
        testProjectDTO2.setFunding(testSet2);

        projectDTOList.add(testProjectDTO);
        projectDTOList.add(testProjectDTO2);
    }

    @Test
    void userTotalFunded() {
        assertEquals(BigDecimal.valueOf(1000), UserService.getInstance().userTotalFunded(testUser, projectDTOList), "Test 01 failed - calculation not correct");

    }
}
