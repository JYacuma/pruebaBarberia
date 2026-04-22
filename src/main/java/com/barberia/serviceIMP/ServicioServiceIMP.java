package com.barberia.serviceIMP;

import com.barberia.dto.ServicioDTO;
import com.barberia.model.Servicio;
import com.barberia.repository.ServicioRepository;
import com.barberia.service.ServicioService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioServiceIMP implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceIMP(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    /* -- listar todos los servicios -- */
    @Override
    public List<ServicioDTO> listarTodos() {
        return servicioRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- buscar servicio por id -- */
    @Override
    public ServicioDTO buscarPorId(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
        return toDTO(servicio);
    }

    /* -- crear nuevo servicio -- */
    @Override
    public ServicioDTO crear(ServicioDTO dto) {
        return toDTO(servicioRepository.save(toEntity(dto)));
    }

    /* -- actualizar servicio existente -- */
    @Override
    public ServicioDTO actualizar(Long id, ServicioDTO dto) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setPrecio(dto.getPrecio());
        servicio.setDuracionMinutos(dto.getDuracionMinutos());
        return toDTO(servicioRepository.save(servicio));
    }

    /* -- eliminar servicio por id -- */
    @Override
    public void eliminar(Long id) {
        servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
        servicioRepository.deleteById(id);
    }

    /* -- convertir entidad a dto -- */
    private ServicioDTO toDTO(Servicio s) {
        ServicioDTO dto = new ServicioDTO();
        dto.setId(s.getId());
        dto.setNombre(s.getNombre());
        dto.setDescripcion(s.getDescripcion());
        dto.setPrecio(s.getPrecio());
        dto.setDuracionMinutos(s.getDuracionMinutos());
        return dto;
    }

    /* -- convertir dto a entidad -- */
    private Servicio toEntity(ServicioDTO dto) {
        Servicio s = new Servicio();
        s.setNombre(dto.getNombre());
        s.setDescripcion(dto.getDescripcion());
        s.setPrecio(dto.getPrecio());
        s.setDuracionMinutos(dto.getDuracionMinutos());
        return s;
    }
}
