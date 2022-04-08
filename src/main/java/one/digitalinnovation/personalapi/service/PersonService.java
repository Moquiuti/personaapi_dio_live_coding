package one.digitalinnovation.personalapi.service;


import lombok.NonNull;
import one.digitalinnovation.personalapi.dto.request.PersonDTO;
import one.digitalinnovation.personalapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personalapi.entity.Person;
import one.digitalinnovation.personalapi.exception.PersonNotFoundException;
import one.digitalinnovation.personalapi.mapper.PersonMapper;
import one.digitalinnovation.personalapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository repository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public MessageResponseDTO createPerson(@NonNull final PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = repository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID: ");
    }

    public List<PersonDTO> listAll() {
        final var allPeople = repository.findAll();
        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(@NonNull final Long id) throws PersonNotFoundException {
        final var person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public void deleteById(@NonNull final Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        repository.deleteById(id);
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        personDTO.setId(id);
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = repository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Updated person with ID: ");
    }

    private MessageResponseDTO createMessageResponse(@NonNull final Long id, @NonNull final String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
