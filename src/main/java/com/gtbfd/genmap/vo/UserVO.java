package com.gtbfd.genmap.vo;

import java.time.LocalDate;

public record UserVO(Long id, String name, String lastname, LocalDate expiresIn){
}
