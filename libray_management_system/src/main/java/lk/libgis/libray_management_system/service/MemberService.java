/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.libgis.libray_management_system.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.libgis.libray_management_system.dto.MemberDTO;
import lk.libgis.libray_management_system.entity.Member;
import lk.libgis.libray_management_system.repo.MemberRepo;

/**
 *
 * @author Chamod Abeywickrama
 */
public class MemberService {
    
    private final MemberRepo memberRepo = new MemberRepo();
    
    public boolean addMember(MemberDTO member){
        try {
            Member entity = this.convertDTOtoEntity(member);
            return memberRepo.saveMember(entity);

        } catch (ClassNotFoundException ex) {
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean delete(String id){
        try {
            boolean delete = memberRepo.delete(id);
            return delete;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean update(MemberDTO member) {
        Member entity = convertDTOtoEntity(member);
        try {
            return memberRepo.update(entity);
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
    }
    
    public Optional<Object> search(String id){
        try{
            Optional<Member> member = memberRepo.searchCustomer(id);
            if(member.isPresent()){
                MemberDTO memberDTO = convertEntityToDTO(member.get());
                return Optional.of(memberDTO);
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    public List<MemberDTO> getAll() {
        try {
            List<Member> all= memberRepo.getAll();
            if (all.isEmpty()){
                throw new RuntimeException();
            }
            List<MemberDTO>memberDTOS = new ArrayList<>();
            for (Member member : all){
                MemberDTO memberDTO = convertEntityToDTO(member);
                memberDTOS.add(memberDTO);
            }
            return memberDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    private Member convertDTOtoEntity(MemberDTO memberDTO){
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setAddress(memberDTO.getAddress());
        member.setEmail(memberDTO.getEmail());
        member.setContact(memberDTO.getContact());
        return member;
    }
    
    private MemberDTO convertEntityToDTO(Member memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setAddress(memberEntity.getAddress());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setContact(memberEntity.getContact());
        return memberDTO;
    }
    
}
