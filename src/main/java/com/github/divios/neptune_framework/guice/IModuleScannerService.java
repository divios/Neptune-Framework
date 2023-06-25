package com.github.divios.neptune_framework.guice;

import com.github.divios.neptune_framework.guice.impl.ModuleScannerServiceImpl;
import com.google.inject.ImplementedBy;
import com.google.inject.Module;

@ImplementedBy(ModuleScannerServiceImpl.class)
public interface IModuleScannerService {

    /**
     * Returns all configuration classes that extends the module interface of
     * google guice in the project
     */
    Module[] getAllModules(String packageName);

}
