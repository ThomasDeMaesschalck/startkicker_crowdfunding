package be.hogent.startkicker.service;

import be.hogent.startkicker.business.ProjectStatus;
import be.hogent.startkicker.service.dto.FundingDTO;
import be.hogent.startkicker.service.dto.ProjectDTO;
import be.hogent.startkicker.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class ProjectServiceTest {

    private UserDTO testUser;
    private UserDTO testUser2;
    private ProjectDTO testProjectDTO;
    private FundingDTO funding1;
    private Set<FundingDTO> testSet;

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

        funding1 = new FundingDTO();
        funding1.setAmount(BigDecimal.valueOf(300));
        funding1.setUser(testUser);
        funding1.setProject(testProjectDTO);


    testSet = new HashSet<>();
    testSet.add(funding1);

    testProjectDTO.setFunding(testSet);
    }

    @Test
    void adjustProjectAndFundingStatus() {
        assertEquals(ProjectStatus.Created, ProjectService.getInstance().adjustProjectAndFundingStatus(testProjectDTO, testUser).getStatus(), "Test 01 failed -- ProjectStatus should be active");
        assertEquals(false, ProjectService.getInstance().adjustProjectAndFundingStatus(testProjectDTO, testUser).isProjectEndDateReached(), "Test 02 failed -- project did not hit end date");
        assertEquals(true, ProjectService.getInstance().adjustProjectAndFundingStatus(testProjectDTO, testUser).isUserHasFunded(), "Test 03 failed -- user did fund this project");

        testProjectDTO.setEndDate(LocalDate.now().minusDays(1));
        assertEquals(true, ProjectService.getInstance().adjustProjectAndFundingStatus(testProjectDTO, testUser).isProjectEndDateReached(), "Test 04 failed -- Project did hit end date");

    }

    @Test
    void funded() {
        ProjectService.getInstance().adjustProjectAndFundingStatus(testProjectDTO, testUser);
        assertEquals(BigDecimal.valueOf(300), ProjectService.getInstance().funded(testProjectDTO), "Test 05 failed - funding total not correct");
    }

    @Test
    void getPercentageFunded() {
        ProjectService.getInstance().adjustProjectAndFundingStatus(testProjectDTO, testUser);
        assertEquals(6, ProjectService.getInstance().getPercentageFunded(testProjectDTO), "Test 06 failed - percentage not correct");
    }

      @Test
    void switchFundingOff() {
        ProjectDTO test = new ProjectDTO();
        test.setProjectEndDateReached(true);
        ProjectService.getInstance().switchFundingOff(testProjectDTO);
        assertEquals(test.isProjectEndDateReached(), testProjectDTO.isProjectEndDateReached());
    }
}
