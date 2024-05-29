package mhha.springmhha.controller.angular

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.sqlSpring.angular.*
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.AngularDocService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "DocMenuController")
@RestController
@RequestMapping(value = ["/angular/doc/menu"])
class DocMenuController {
    @Autowired lateinit var responseService: ResponseService
    @Autowired lateinit var angularDocService: AngularDocService
    @GetMapping(value = ["/get/components/tab"])
    fun getComponentsTab(): IRestResult {
        return responseService.getResult(angularDocService.getComponentsTabAll())
    }
    @PostMapping(value = ["/post/components/tab"])
    fun postComponentsTab(): IRestResult {
        val temp = DocComponentsTab().apply {
            name = "AccordionTab"
            description = "AccordionTab is a helper component for Accordion."
            docComponentsProps = DocComponentsProps().apply {
                description = "Defines the input properties of the component."
                docComponentsPropsValue = arrayListOf<DocComponentsPropsValue>().apply {
                    add(DocComponentsPropsValue().apply {
                        name = "id"
                        optional = false
                        readonly = false
                        type = "string"
                        description = "Current id state as a string."
                    })
                    add(DocComponentsPropsValue().apply {
                        name = "header"
                        optional = false
                        readonly = false
                        type = "string"
                        description = "Used to define the header of the tab."
                    })
                    add(DocComponentsPropsValue().apply {
                        name = "headerStyle"
                        optional = false
                        readonly = false
                        type = "Object"
                        description = "Inline style of the tab header."
                    })
                }
            }
            docComponentsEmits = DocComponentsEmits().apply {
                description = "Defines emit that determine the behavior of the component based on a given condition or report the actions that the component takes."
                docComponentsEmitsValue = arrayListOf<DocComponentsEmitsValue>().apply {
                    add(DocComponentsEmitsValue().apply {
                        name = "selectedChange"
                        description = "Event triggered by changing the choice."
                        docComponentsEmitsValueParameters = arrayListOf<DocComponentsEmitsValueParameters>().apply {
                            add(DocComponentsEmitsValueParameters().apply {
                                name = "value"
                                type = "boolean"
                            })
                        }
                    })
                }
            }
        }
        temp.setChild()
        angularDocService.saveComponentsTab(temp)

        return responseService.getSuccessResult()
    }
}