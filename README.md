### Backend del juego Pyramid
Un backend que responde a rutas especificas mencionadas en la posterior mente.

Para el desarrolo del juego Pyrmid, el cual consiste en poder agrupar 2 cartas de una baraja de poker cuya suma de sus pesos sea de 13 .

**Contenidos**

[TOCM]

[TOC]

#Rutas 

##Inicio del juego [](https://github.com/pandao/editor.md "Heading link")
Se enviara una solicitud **POST** a la ruta **Game/start**, en donde se envia como parametro un **Json** en el cual se lista las primeras cartas a almacenar.
##Insertar [](https://github.com/pandao/editor.md "Heading link")
Se enviara una solicitud **POST** a la ruta **Game/add**, en donde se envia como parametro un **Json** en el cual se lista la carta a insertar.
##Eliminar [](https://github.com/pandao/editor.md "Heading link")
Se enviara una solicitud **DELETE** a la ruta **Game/delete**, en donde se envia como parametro un **Json** la pareja de cartas a eliminar o carta **K** (ya que su valor es 13).
##Obtener estado del árbol avl [](https://github.com/pandao/editor.md "Heading link")
Se enviara una solicitud **GET** a la ruta **Game/status-avltree**, en donde se recibira una ruto para la observacion de una imagen por el estado del arbol.
##Obtener nivel [](https://github.com/pandao/editor.md "Heading link")
Se enviara una solicitud **GET** a la ruta **Game/get-level?level={no.level}**, donde se envia el parametro level indicando el nivel que desea returnar, de froma se obtendra un **JSON**.
##Obtener Árbol-(PreOrder, InOrder, PostOrder)[](https://github.com/pandao/editor.md "Heading link")
Se enviara una solicitud **GET** a la ruta **Game/avltree?transversal={preOrder/inOrder/postOrder}**, donde se envia el parametro transversal que unicamente pode ser ~~{preOrder/inOrder/postOrder}~~ que desea returnar, de froma se obtendra un **JSON**.
#Códigos de respuesta 
| **Error**  |  **Código de Respuesta** |
| ------------ | ------------ |
| La carta no se encuentra en el árbol avl (eliminar)  |  Status Code 404 |
| Los valores de las cartas no suman 13  | Status Code 406  |
|  La carta no se puede eliminar ya que cuenta con hijos |  Status Code 409 |
|  La carta a insertar esta duplicada |  Status Code 406 |
|Cualquier otro error   |  Status Code 400 |
#Corrimiento
| **Tipo**  |  **Corrimiento** |
| ------------ | ------------ |
| Trebol | 0 |
| Diamante | 20|
|  Corazón|  40 |
|  Pica |  60|
