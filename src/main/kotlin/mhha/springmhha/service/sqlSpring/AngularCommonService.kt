package mhha.springmhha.service.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.doc.*
import mhha.springmhha.model.sqlSpring.angular.news.NewsItem
import mhha.springmhha.repository.sqlSpring.doc.*
import mhha.springmhha.repository.sqlSpring.news.NewsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AngularCommonService {
    @Autowired lateinit var docPage: DocPageRepository
    @Autowired lateinit var docComponents: DocComponentsRepository
    @Autowired lateinit var docComponentsProps: DCPRepository
    @Autowired lateinit var docComponentsPropsValues: DCPVRepository
    @Autowired lateinit var docComponentsEmits: DCERepository
    @Autowired lateinit var docComponentsEmitsValue: DCEVRepository
    @Autowired lateinit var docComponentsEmitsValueParameters: DCEVPRepository
    @Autowired lateinit var docInterfaces: DocInterfacesRepository
    @Autowired lateinit var docInterfacesEvents: DIERepository
    @Autowired lateinit var docInterfacesEventsValue: DIEVRepository
    @Autowired lateinit var docInterfacesEventsValueProps: DIEVPRepository
    @Autowired lateinit var docInterfacesTemplates: DITRepository
    @Autowired lateinit var docInterfacesTemplatesValue: DITVRepository
    @Autowired lateinit var docInterfacesTemplatesValueParameters: DITVPRepository
    @Autowired lateinit var newsRepository: NewsRepository
    @Autowired lateinit var docMenuRepository: DocMenuItemRepository

    fun getPage() = docPage.findAll()
    fun getPageByIndex(index: Long) = docPage.findByThisIndex(index)
    fun getPageByName(name: String) = docPage.findByName(name)
    fun getComponents() = docComponents.findAll()
    fun getComponentsPropsValues() = docComponentsPropsValues.findAll()
    fun getComponentsEmits() = docComponentsEmits.findAll()
    fun getComponentsProps() = docComponentsEmitsValue.findAll()
    fun getComponentsEmitsValue() = docComponentsProps.findAll()
    fun getComponentsEmitsValueParameters() = docComponentsEmitsValueParameters.findAll()
    fun getInterfaces() = docInterfaces.findAll()
    fun getInterfacesEvents() = docInterfacesEvents.findAll()
    fun getInterfacesEventsValue() = docInterfacesEventsValue.findAll()
    fun getInterfacesEventsValueProps() = docInterfacesEventsValueProps.findAll()
    fun getInterfacesTemplates() = docInterfacesTemplates.findAll()
    fun getInterfacesTemplatesValue() = docInterfacesTemplatesValue.findAll()
    fun getInterfacesTemplatesValueParameters() = docInterfacesTemplatesValueParameters.findAll()

    fun savePage(data: DocPage) = docPage.save(data)
    fun savePage(data: List<DocPage>) = docPage.saveAll(data)
    fun saveComponents(data: DocComponents) = docComponents.save(data)
    fun setComponentsPropsValues(data: DocComponentsPropsValues) = docComponentsPropsValues.save(data)
    fun setComponentsEmits(data: DocComponentsEmits) = docComponentsEmits.save(data)
    fun setComponentsProps(data: DocComponentsEmitsValues) = docComponentsEmitsValue.save(data)
    fun setComponentsEmitsValue(data: DocComponentsProps) = docComponentsProps.save(data)
    fun setComponentsEmitsValueParameters(data: DocComponentsEmitsValuesParameters) = docComponentsEmitsValueParameters.save(data)
    fun setInterfaces(data: DocInterfaces) = docInterfaces.save(data)
    fun setInterfacesEvents(data: DocInterfacesEvents) = docInterfacesEvents.save(data)
    fun setInterfacesEventsValue(data: DocInterfacesEventsValues) = docInterfacesEventsValue.save(data)
    fun setInterfacesEventsValueProps(data: DocInterfacesEventsValuesProps) = docInterfacesEventsValueProps.save(data)
    fun setInterfacesTemplates(data: DocInterfacesTemplates) = docInterfacesTemplates.save(data)
    fun setInterfacesTemplatesValue(data: DocInterfacesTemplatesValues) = docInterfacesTemplatesValue.save(data)
    fun setInterfacesTemplatesValueParameters(data: DocInterfacesTemplatesValuesParameters) = docInterfacesTemplatesValueParameters.save(data)

    fun getNewsAll() = newsRepository.findAllByOrderByThisIndexDesc()
    fun getNewsItem() = newsRepository.findFirstByOrderByThisIndexDesc()
    fun addNewsItem(data: NewsItem) = newsRepository.save(data)
    fun addNewsItem(data: List<NewsItem>) = newsRepository.saveAll(data)

    fun getDocMenuAll(isDesc: Boolean = false) = if (isDesc) {
        docMenuRepository.findAllByOrderByThisIndexDesc()
    } else {
        docMenuRepository.findAllByOrderByThisIndexAsc()
    }
    fun getDocMenu(name: String) = docMenuRepository.findByName(name)
    fun getDocMenu(name: List<String>) = docMenuRepository.findAllByNameIn(name)
    fun addDocMenuItem(data: DocMenuItem) = docMenuRepository.save(data)
    fun addDocMenuItem(data: List<DocMenuItem>) = docMenuRepository.saveAll(data)
}