package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.DocCategory;
import de.elvirakraft.docmanagement.entities.Document;
import de.elvirakraft.docmanagement.entities.User;
import de.elvirakraft.docmanagement.entities.UserRole;
import de.elvirakraft.docmanagement.repositories.DocCategoryRepository;
import de.elvirakraft.docmanagement.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DocCategoryService {

    private final DocCategoryRepository docCategoryRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocCategoryService(DocCategoryRepository docCategoryRepository, DocumentRepository documentRepository){
        this.docCategoryRepository = docCategoryRepository;
        this.documentRepository = documentRepository;
    }

    /**
     * Adds the given document category to the database.
     *
     * @param docCategory The given category of a document.
     * @return The added document category.
     */
    public DocCategory createDocCategory(DocCategory docCategory) {
        if (docCategoryRepository.findDocCategoryByName(docCategory.getName()).isPresent()) {
            return null;
        }
        return docCategoryRepository.save(docCategory);
    }

    /**
     * Adds the given category to the given document
     *
     * @param document The given document
     * @param docCategoryId The id of the given document category
     * @return The updated document
     */
    public Document addCategoryToTheDocument(Document document, Integer docCategoryId) {
        DocCategory docCategoryToAdd = docCategoryRepository.findById(docCategoryId).orElseThrow(EntityNotFoundException::new);
        docCategoryToAdd.getDocumentsOfTheCategory().add(document);
        document.getDocCategories().add(docCategoryToAdd);
        return documentRepository.save(document);
    }

    /**
     * Deletes the given category from the given document
     *
     * @param documentId The id of the given document
     * @param docCategoryId The id of the given document category
     * @return The updated document
     */
    public Document deleteCategoryFromTheDocument(Long documentId, Integer docCategoryId) {
        DocCategory docCategoryToRemove = docCategoryRepository.findById(docCategoryId).orElseThrow(EntityNotFoundException::new);
        Document documentToUpdate = documentRepository.findById(documentId).orElseThrow(EntityNotFoundException::new);
        docCategoryToRemove.getDocumentsOfTheCategory().remove(documentRepository.getById(documentId));
        documentToUpdate.getDocCategories().remove(docCategoryToRemove);
        return documentRepository.save(documentToUpdate);
    }

    /**
     * Edits the given document category.
     *
     * @param docCategory The given document category.
     * @return The edited document category.
     */
    public DocCategory updateDocCategory(DocCategory docCategory) throws EntityNotFoundException {
        DocCategory docCategoryToUpdate = docCategoryRepository.findById(docCategory.getId()).orElseThrow(EntityNotFoundException::new);
        if (docCategory.getName() != null) docCategoryToUpdate.setName(docCategory.getName());
       return docCategoryRepository.save(docCategoryToUpdate);
    }

    /**
     * Removes an association between a document and a document category for the following deletion of this category
     *
     * @param categoryId The id of the category to be deleted.
     */
    public void removeDocumentDoccategoryAssociation(Integer categoryId) {
        DocCategory docCategoryInAssoc = docCategoryRepository.getById(categoryId);
        for (Document doc: docCategoryInAssoc.getDocumentsOfTheCategory()) {
            doc.removeDocCategory(docCategoryInAssoc);
        }
    }

    /**
     * Deletes the document category with the given ID.
     *
     * @param id The id of the given category.
     * @return The deleted document category.
     */
    public DocCategory deleteDocCategory(Integer id) {
        Optional<DocCategory> optionalDocCategory = docCategoryRepository.findById(id);
        if (optionalDocCategory.isEmpty()) {
            return null;
        }
        DocCategory docCategoryToDelete = optionalDocCategory.get();
        removeDocumentDoccategoryAssociation(id);
        docCategoryRepository.delete(docCategoryToDelete);
        return docCategoryToDelete;
    }

    /**
     * Returns the document category with the given ID.
     *
     * @param id The given ID.
     * @return The document category.
     */
    public DocCategory getDocCategoryById(Integer id) {
        return docCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Returns all document categories.
     *
     * @return The document categories.
     */
    public List<DocCategory> getAllDocCategories() {
        return docCategoryRepository.findAll();
    }

    /**
     * Returns all the documents, that have the given category
     *
     * @param categoryId The id of the given category
     * @return List of the documents
     */
    public List<Document> getAllDocsOfTheCategory(Integer categoryId) {
        Set<Document> allDocs = docCategoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new).getDocumentsOfTheCategory();
        allDocs.removeIf(Document::isDeleted);
        return new ArrayList<>(allDocs);
    }


}
