package mhha.springmhha.service.sqlSpring

import mhha.springmhha.model.sqlSpring.angular.DocComponentsTab
import mhha.springmhha.repository.sqlSpring.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AngularDocService {
    @Autowired lateinit var docComponentsTab: DCTRepository
    @Autowired lateinit var docComponentsProps: DCPRepository
    @Autowired lateinit var docComponentsPropsValues: DCPVRepository
    @Autowired lateinit var docComponentsEmits: DCERepository
    @Autowired lateinit var docComponentsEmitsValue: DCEVRepository
    @Autowired lateinit var docComponentsEmitsValueParameters: DCEVPRepository

    fun getComponentsTabAll() = docComponentsTab.findAll()
    fun saveComponentsTab(data: DocComponentsTab) = docComponentsTab.save(data)
}