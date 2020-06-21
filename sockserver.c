/*
  Copyright: InExInferis Inc.
  URL: http://www.inexinferis.co.nr
  Author: Karman
  Date: 26/10/05 13:27
*/
//gcc sockserver.c -o socket -lws2_32
#include <stdio.h>
#include <windows.h>

char SendBuff[512],RecvBuff[512];

int main(int argc, char *argv[]){

  WSADATA wsaData;
  SOCKET conn_socket,comm_socket;
  SOCKET comunicacion;
  struct sockaddr_in server;
  struct sockaddr_in client;
  struct hostent *hp;
  int resp,stsize;

  //Inicializamos la DLL de sockets
  resp=WSAStartup(MAKEWORD(1,0),&wsaData);
  if(resp){
    printf("Error al inicializar socket\n");
    getchar();return resp;
  }

  //Obtenemos la IP que usará nuestro servidor...
  // en este caso localhost indica nuestra propia máquina...
  hp=(struct hostent *)gethostbyname("localhost");

  if(!hp){
    printf("No se ha encontrado servidor...\n");
    getchar();WSACleanup();return WSAGetLastError();
  }

  // Creamos el socket...
  conn_socket=socket(AF_INET,SOCK_STREAM, 0);
  if(conn_socket==INVALID_SOCKET) {
    printf("Error al crear socket\n");
    getchar();WSACleanup();return WSAGetLastError();
  }

  memset(&server, 0, sizeof(server)) ;
  memcpy(&server.sin_addr, hp->h_addr, hp->h_length);
  server.sin_family = hp->h_addrtype;
  server.sin_port = htons(6000);

  // Asociamos ip y puerto al socket
  resp=bind(conn_socket, (struct sockaddr *)&server, sizeof(server));
  if(resp==SOCKET_ERROR){
    printf("Error al asociar puerto e ip al socket\n");
    closesocket(conn_socket);WSACleanup();
    getchar();return WSAGetLastError();
  }

  if(listen(conn_socket, 4)==SOCKET_ERROR){
    printf("Error al habilitar conexiones entrantes\n");
    closesocket(conn_socket);WSACleanup();
    getchar();return WSAGetLastError();
  }
  int a = 0;




  // Aceptamos conexiones entrantes
  printf("Esperando conexiones entrantes... \n");
  stsize=sizeof(struct sockaddr);
  comm_socket=accept(conn_socket,(struct sockaddr *)&client,&stsize);
  if(comm_socket==INVALID_SOCKET){
    printf("Error al aceptar conexión entrante\n");
    closesocket(conn_socket);WSACleanup();
    getchar();return WSAGetLastError();
  }
  printf("Conexion entrante desde: %s\n", inet_ntoa(client.sin_addr));

  //Como no vamos a aceptar más conexiones cerramos el socket escucha
  closesocket(conn_socket);


  printf("Recibiendo Mensaje... \n");
  recv (comm_socket, RecvBuff, sizeof(RecvBuff), 0);
  printf("Datos recibidos: %s \n", RecvBuff);


  //vamos a responder
  strcpy(SendBuff,"Mensaje desde el server."); //copia el string al buffer
  //Enviamos datos...
  printf("Enviando Mensaje... \n");
  send (comm_socket, SendBuff, sizeof(SendBuff), 0);


  //getchar(); //lee input de terminal (?)

  closesocket(comm_socket);
  // Cerramos liberia winsock
  WSACleanup();


  return (EXIT_SUCCESS);
}
