package mhha.springmhha.controller.angular

import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.advice.exception.NotValidOperationException
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.sqlSpring.angular.*
import mhha.springmhha.service.common.ResponseService
import mhha.springmhha.service.sqlSpring.AngularDocService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "DocMenuController")
@RestController
@RequestMapping(value = ["/angular/doc/menu"])
class DocMenuController {
    @Autowired lateinit var responseService: ResponseService
    @Autowired lateinit var angularDocService: AngularDocService

    @GetMapping(value = ["/get/doc"])
    fun getDocPage(): IRestResult {
        return responseService.getResult(angularDocService.getPage())
    }
    @GetMapping(value = ["/get/doc/{index}"])
    fun getDocPage(@PathVariable index: Long): IRestResult {
        return responseService.getResult(angularDocService.getPageByIndex(index))
    }
    @PostMapping(value = ["/post/doc/test"])
    fun postDocPage(): IRestResult {
        if (angularDocService.getPageByName("accordion") != null) {
            throw NotValidOperationException()
        }

        val temp = mutableListOf<DocPage>().apply {
            add(DocPage().apply {
                name = "accordion"
                docComponents = DocComponents().apply {
                    name = "AccordionTab"
                    description = "AccordionTab is a helper component for Accordion."
                    docComponentsProps = DocComponentsProps().apply {
                        description = "Defines the input properties of the component."
                        docComponentsPropsValues = arrayListOf<DocComponentsPropsValues>().apply {
                            add(DocComponentsPropsValues().apply {
                                name = "id"
                                optional = false
                                readonly = false
                                type = "string"
                                description = "Current id state as a string."
                            })
                            add(DocComponentsPropsValues().apply {
                                name = "header"
                                optional = false
                                readonly = false
                                type = "string"
                                description = "Used to define the header of the tab."
                            })
                            add(DocComponentsPropsValues().apply {
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
                        docComponentsEmitsValues = arrayListOf<DocComponentsEmitsValues>().apply {
                            add(DocComponentsEmitsValues().apply {
                                name = "selectedChange"
                                description = "Event triggered by changing the choice."
                                docComponentsEmitsValuesParameters = arrayListOf<DocComponentsEmitsValuesParameters>().apply {
                                    add(DocComponentsEmitsValuesParameters().apply {
                                        name = "value"
                                        type = "boolean"
                                    })
                                }
                            })
                        }
                    }
                }
                docInterfaces = DocInterfaces().apply {
                    docInterfacesEvents = DocInterfacesEvents().apply {
                        description = "Defines the custom events used by the component's emitters."
                        docInterfacesEventsValues = arrayListOf<DocInterfacesEventsValues>().apply {
                            add(DocInterfacesEventsValues().apply {
                                name = "AccordionTabOpenEvent"
                                description = "Custom tab open event."
                                docInterfacesEventsValuesProps = arrayListOf<DocInterfacesEventsValuesProps>().apply {
                                    add(DocInterfacesEventsValuesProps().apply {
                                        name = "originalEvent"
                                        optional = false
                                        readonly = false
                                        type = "Event"
                                        description = "Browser event."
                                    })
                                    add(DocInterfacesEventsValuesProps().apply {
                                        name = "index"
                                        optional = false
                                        readonly = false
                                        type = "number"
                                        description = "Opened tab index."
                                    })
                                }
                            })
                            add(DocInterfacesEventsValues().apply {
                                name = "AccordionTabCloseEvent"
                                description = "Custom tab close event."
                                docInterfacesEventsValuesProps = arrayListOf<DocInterfacesEventsValuesProps>().apply {
                                    add(DocInterfacesEventsValuesProps().apply {
                                        name = "originalEvent"
                                        optional = false
                                        readonly = false
                                        type = "Event"
                                        description = "Browser event."
                                    })
                                    add(DocInterfacesEventsValuesProps().apply {
                                        name = "index"
                                        optional = false
                                        readonly = false
                                        type = "number"
                                        description = "Opened tab index."
                                    })
                                }
                            })
                        }
                    }
                    docInterfacesTemplates = DocInterfacesTemplates().apply {
                        description = "Defines the templates used by the component."
                        docInterfacesTemplatesValues = arrayListOf<DocInterfacesTemplatesValues>().apply {
                            add(DocInterfacesTemplatesValues().apply {
                                parent = "accordion"
                                name = "content"
                                description = "Custom template of content."
                            })
                            add(DocInterfacesTemplatesValues().apply {
                                parent = "accordion"
                                name = "header"
                                description = "Custom template of header."
                            })
                            add(DocInterfacesTemplatesValues().apply {
                                parent = "accordion"
                                name = "icon"
                                description = "Custom template of icon."
                                docInterfacesTemplatesValuesParameters = arrayListOf<DocInterfacesTemplatesValuesParameters>().apply {
                                    add(DocInterfacesTemplatesValuesParameters().apply {
                                        name = "context"
                                        type = "{\n  \t \$implicit: any, // Data of the selected.\n  }"
                                    })
                                }
                            })
                        }
                    }
                }
            }.setChild())
            add(DocPage().apply {
                name = "animate"
                docComponents = DocComponents().apply {
                    name = "Animate"
                    description = "Animate manages PrimeFlex CSS classes declaratively to during enter/leave animations on scroll or on page load."
                    docComponentsProps = DocComponentsProps().apply {
                        description = "Defines the input properties of the component."
                        docComponentsPropsValues = arrayListOf<DocComponentsPropsValues>().apply {
                            add(DocComponentsPropsValues().apply {
                                name = "enterClass"
                                optional = false
                                readonly = false
                                type = "string"
                                description = "Selector to define the CSS class for enter animation."
                            })
                            add(DocComponentsPropsValues().apply {
                                name = "leaveClass"
                                optional = false
                                readonly = false
                                type = "string"
                                description = "Selector to define the CSS class for leave animation."
                            })
                        }
                    }
                }
            }.setChild())
            add(DocPage().apply {
                name = "confirmation"
                docInterfaces = DocInterfaces().apply {
                    description = "Defines the custom interfaces used by the module."
                    docInterfacesValues = arrayListOf<DocInterfacesValues>().apply {
                        add(DocInterfacesValues().apply {
                            name = "Confirmation"
                            description = "Represents a confirmation dialog configuration."
                            docInterfacesValuesProps = arrayListOf<DocInterfacesValuesProps>().apply {
                                add(DocInterfacesValuesProps().apply {
                                    name = "message"
                                    optional = true
                                    readonly = false
                                    type = "string"
                                    description = "The message to be displayed in the confirmation dialog."
                                })
                                add(DocInterfacesValuesProps().apply {
                                    name = "key"
                                    optional = true
                                    readonly = false
                                    type = "string"
                                    description = "A unique key to identify the confirmation dialog."
                                })
                                add(DocInterfacesValuesProps().apply {
                                    name = "icon"
                                    optional = true
                                    readonly = false
                                    type = "string"
                                    description = "The name of the icon to be displayed in the confirmation dialog."
                                })
                                add(DocInterfacesValuesProps().apply {
                                    name = "header"
                                    optional = true
                                    readonly = false
                                    type = "string"
                                    description = "The header text of the confirmation dialog."
                                })
                            }
                        })
                    }
                }
            }.setChild())
        }

        return responseService.getResult(angularDocService.savePage(temp))
    }
}