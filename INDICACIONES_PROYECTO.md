# Sistema de Transacciones Bancarias

## Introducción
Este proyecto implementa un sistema básico de transacciones bancarias desarrollado en Java utilizando Spring Boot. Permite la gestión de clientes, cuentas y transacciones, así como la generación de reportes financieros.

## Objetivo del Proyecto
Desarrollar un sistema que gestione clientes, cuentas y transacciones bancarias, y que permita la generación de reportes financieros.

## Especificaciones Técnicas

### Requisitos Generales
- **Lenguaje y Framework:** Java 11 con Spring Boot
- **Gestión de Dependencias:** Maven
- **Base de Datos:** H2 Database (Base de datos relacional en memoria)
- **Autenticación y Autorización:** Implementación de seguridad básica con Auth Basic
- **Pruebas Unitarias:** Cobertura de código con JUnit y Mockito
- **Documentación:** Código fuente bien documentado con comentarios explicativos

### Entidades y Operaciones

#### Customer (Cliente)
- **Atributos:** ID, nombre, dirección, email, fecha de registro, entre otros
- **Operaciones:** Registro y actualización de clientes, consulta de información, eliminación lógica

#### Account (Cuenta)
- **Atributos:** ID, número de cuenta, saldo actual, ID del cliente asociado, fecha de apertura
- **Operaciones:** Creación y actualización de cuentas, consulta de saldo y detalles, cierre de cuentas

#### Transaction (Transacción)
- **Atributos:** ID, tipo (depósito, retiro), monto, fecha, ID de cuenta asociada, detalles adicionales
- **Operaciones:** Registro de transacciones, consulta de historial por cuenta

#### Report (Reporte)
- **Operaciones:** Generación de reportes de transacciones por cliente o cuenta, filtrado por fechas y tipo

### Queries Dinámicos
Implementación de consultas dinámicas para búsquedas complejas en la base de datos.

## Evaluación y Entrega
La calidad del proyecto se evaluará en función de:
- Calidad del código
- Funcionalidad
- Aplicación de principios de diseño y patrones de software
- Cumplimiento de los requisitos
- Cobertura de pruebas unitarias

La entrega del proyecto se realizará a través de un repositorio público en la fecha acordada.

## Autorización y Autenticación
Se utiliza Auth Basic para la autorización y autenticación de usuarios en el sistema.

## Ejecución del Proyecto
Para ejecutar el proyecto, asegúrese de tener Maven y Java 11 instalados. Ejecute el siguiente comando:
```bash
mvn spring-boot:run
```

## Contribuir
Para contribuir al proyecto, siga estos pasos:
1. Haga un fork del repositorio.
2. Cree una nueva rama (`git checkout -b feature/nuevaFuncionalidad`).
3. Haga sus cambios y agregue sus commits (`git commit -am 'Añadir algunos cambios'`).
4. Empuje a la rama (`git push origin feature/nuevaFuncionalidad`).
5. Cree una nueva Pull Request.

## Licencia
Este proyecto se publica bajo MIT, lo que significa que puede usar, modificar, fusionar, publicar, distribuir, sublicenciar y/o vender copias del software bajo estos términos.

## Contacto
Para más información o consultas, contacte a Joel Arauzo o abra un issue en el repositorio del proyecto.
