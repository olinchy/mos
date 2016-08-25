package com.zte.mos.service.impl;


public class VmServices {

    //private static IModelService modelService;

    private VmServices() {
        new PersistenceService().start();
        new ConvertService().start();
        new MetaService().start();
//        modelService = new ModelService();
//        modelService.start();
    }

//    public static IModelService getModelService(){
//        return modelService;
//    }

//    public static void setModelService(IModelService userSv){
//        if(userSv != null){
//            modelService = userSv;
//        }
//    }

    public static VmServices getInstance(){
        return Holder.instance;
    }


    private static class Holder{
        static final VmServices instance = new VmServices();
    }


}
