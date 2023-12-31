package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端密码进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        System.out.println("当前线程的id" + Thread.currentThread().getId());
        Employee employee = new Employee();
        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //设置添加人id
        //employee.setCreateUser(BaseContext.getCurrentId());
        //employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insertEmployee(employee);

    }
    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuary(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> employeeList = page.getResult();

        return new PageResult(total,employeeList);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }
    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Override
    public Employee queryById(Long id) {
        Employee employee = employeeMapper.queryById(id);
        employee.setPassword("****");
        return employee;
    }
    /**
     * 修改员工信息
     * @param employeeDTO
     * @return
     */
    @Override
    public void updateEmp(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

}
