package com.nambi.book.springboot.service.system;


import com.nambi.book.springboot.domain.system.MenuRepository;
import com.nambi.book.springboot.web.dto.system.MenuListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public List<MenuListResponseDto> findAlldesc(){
        return menuRepository.findAllDesc().stream()
                .map(MenuListResponseDto::new)
                .collect(Collectors.toList());
    }

}
