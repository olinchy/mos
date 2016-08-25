cmake_minimum_required(VERSION 2.8)
project(modelgen)
set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "$ENV{CMAKE_TOOLS}/Modules/")
include(ems_model_generator)


#file(GLOB_RECURSE CURRENT_MODEL $ENV{MOS_HOME}/develop/model_mw/mo/$ENV{MODEL}/*.yml $ENV{MOS_HOME}/develop/model_mw/mo/all/*.yml)
#file(GLOB_RECURSE CURRENT_ENUM  $ENV{MOS_HOME}/develop/model_mw/enum/$ENV{MODEL}/*.yml $ENV{MOS_HOME}/develop/model_mw/enum/all/*.yml)

#set(GENERATED_MO_DIR ${PROJECT_SOURCE_DIR}/model/$ENV{MODEL_FOLDER}/src/com/zte/mos/domain/model/autogen/nr$ENV{MODEL})
#set(PERSISTENCE_CFG_DIR ${PROJECT_SOURCE_DIR}/model/$ENV{MODEL_FOLDER}/model/nr$ENV{MODEL}/persistence)

#set(EMS_MODEL_DIR ${PROJECT_SOURCE_DIR}/model/mos_model_ems/model/ems/mo)
#set(NR8000_MODELDIR $ENV{MOS_HOME}/develop/model_mw/mo/$ENV{MODEL})

#add_mo_files(GENERATED_FILES ${CURRENT_MODEL})
#add_enum_files(GENERATED_FILES ${CURRENT_ENUM})
#add_custom_target(codegen ALL DEPENDS ${GENERATED_FILES})

add_custom_command(TARGET codegen POST_BUILD
    COMMAND find  -maxdepth 1 -name "*Enum.java" -exec mv {} ${GENERATED_MO_DIR}/enums \;
   VERBATIM)
