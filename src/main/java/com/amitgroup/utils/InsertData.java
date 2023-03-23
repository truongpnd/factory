package com.amitgroup.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.sqldatabase.entities.Area;
import com.amitgroup.sqldatabase.entities.Machine;
import com.amitgroup.sqldatabase.entities.Role;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.repositories.AreaRepository;
import com.amitgroup.sqldatabase.repositories.MachineRepository;
import com.amitgroup.sqldatabase.repositories.RoleRepository;
import com.amitgroup.sqldatabase.repositories.UserRepository;

@org.springframework.stereotype.Service
@RestController
public class InsertData {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/insert")
    public void inserData(){

       try{
        Role role = new Role();
        role.setName("Quản lý");
        Role role2 = new Role();
        role2.setName("Giám sát");
        Role role3 = new Role();
        role3.setName("Thợ");
        roleRepository.save(role);
        roleRepository.save(role2);
        roleRepository.save(role3);

        User user = new User();
        user.setFullname("Phan Nguyễn Đăng Trường");
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        user.setEmail("pndangtruong@gmail.com");
        user.setPhone("0987654321");
        user.setIsActive(true);
        user.setUsername("dangtruong");
        user.setRole(role);;

        User user2 = new User();
        user2.setFullname("Nguyễn Chí Thành");
        user2.setPassword(bCryptPasswordEncoder.encode("123456"));
        user2.setEmail("thanasdasdh@gmail.com");
        user2.setPhone("09876543221");
        user2.setIsActive(true);
        user2.setUsername("truong");
        user2.setRole(role2);;

        User user3 = new User();
        user3.setFullname("Nguyễn Văn Thợ");
        user3.setPassword(bCryptPasswordEncoder.encode("123456"));
        user3.setEmail("thweqweo@gmail.com");
        user3.setPhone("0982654321");
        user3.setIsActive(true);
        user3.setUsername("dang");
        user3.setRole(role3);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        Machine machine = new Machine();
        machine.setCode("M001");
        machine.setName("Máy cắt");
        machine.setDescription("Máy cắt");
        machineRepository.save(machine);

        Machine machine2 = new Machine();
        machine2.setCode("M002");
        machine2.setName("Máy ảnh");
        machine2.setDescription("Máy ảnh");
        machineRepository.save(machine2);

        Machine machine3 = new Machine();
        machine3.setCode("M003");
        machine3.setName("Máy in");
        machine3.setDescription("Máy in");
        machineRepository.save(machine3);

        Area area = new Area();
        area.setName("Khu vực 1");
        area.setDescription("Khu vực 1");
        areaRepository.save(area);

        Area area2 = new Area();
        area2.setName("Khu vực 2");
        area2.setDescription("Khu vực 2");
        areaRepository.save(area2);

        Area area3 = new Area();
        area3.setName("Khu vực 3");
        area3.setDescription("Khu vực 3");
        areaRepository.save(area3);
       }catch(Exception e){
           e.printStackTrace();
       }

    }

}
