# rappi_test
Proyecto de evaluación para Rappi.

## Overview
Lenguaje: Kotlin <br>
Patrón de diseño: MVVM <br>
Librerias utilizadas: Dagger2, Retrofit + OkHttp, Realm, Glide, Mockito, Robolectric, PowerMock. <br>
Herramienta de análisis estático de códgio: detekt

## Estructura del proyecto
<pre>
* dagger          Clases requeridas para la injeccion de código con dagger.
* models          Pojo's para modelar información.
* repository      Repositorio que actua como Single Source of Truth de la applicación.
  * local         DAO's para acceder a datos almacenados localmente utilizando Realm.
  * remote        Service's para obtener datos remotamente.
* utils           Clases utilitarias.
* viewmodels      Implementaciones de los ViewModels utilizados
* views           Implementaciones de las vistas utilizadas.
</pre>
## Arquitectura
![arquitectura](https://github.com/agustin-mounier/rappi_test/blob/master/arq.png)

El proyecto fue desarrollado en kotlin utilizando el patrón de diseño MVVM. <br>
Cada una de las capas de arquitectura interactua con otra de forma unilateral siguiendo la "regla de las dependencias" de la arquitectura CLEAN, logrando un mayor desacoplamiento de código. <br> 
El repositorio actua como una Single Source of Truth de la applicación buscando la información necesaria de forma remota o local dependiendo la conectividad disponible del dispositivo.

## Preguntas
### En qué consiste el principio de responsabilidad única? Cuál es su propósito?
El principio de responsabilidad única nos indica que cada función, clase o módulo debe ser responsable sobre una única parte de la
funcionalidad provista por el software y que ésta responsabilidad debe estar encapsulada por completo en dicha clase.
Es el primero de los cinco principios SOLID, y nos ayuda a modularizar el código haciendolo mas facil de mantener y entender.

### Qué características tiene, según su opinión, un “buen” código o código limpio?
En mi opinión buen codigo es aquel que es facil de entender y mantener. <br>
Esto se logra mediante:
  * Uso de nombres claros y descriptivos para clases, funciones, modulos etc.
  * Utilizando patrones de diseño y arquitecturas conocidas.
  * Desacoplando sus componentes.
  * Desarrollando tests unitarios y de integración.
