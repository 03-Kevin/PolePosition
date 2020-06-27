//
// Created by Manuel Jiménez on 17/6/2020.
//

#ifndef POLEPOSITIONCR_LISTAENLAZADA_H
#define POLEPOSITIONCR_LISTAENLAZADA_H
#include <fstream>
#include <iostream>
#include <string>
#include <stdlib.h>

#include "node.h"
#include "node.cpp"

using namespace std;

template <class T>


class listaenlazada {
public:
    listaenlazada();
    ~listaenlazada();

    void add_head(T);
    void add_end(T);
    void add_sort(T);
    void concat(listaenlazada);
    void del_all();
    void del_by_data(T);
    void del_by_position(int);
    void fill_by_user(int);
    void fill_random(int);
    void intersection(listaenlazada);
    void invert();
    void load_file(string);
    void print();
    void save_file(string);
    void search(T);
    void sort();

private:
    Node<T> *m_head;
    int m_num_nodes;
};


#endif //POLEPOSITIONCR_LISTAENLAZADA_H
