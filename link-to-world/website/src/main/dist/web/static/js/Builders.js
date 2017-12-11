function registerBuilder (name, builder) {
    this.builders[name.toLowerCase()] = builder;
};