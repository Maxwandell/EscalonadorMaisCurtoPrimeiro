package br.ufpb.dcx.aps.escalonador;

import java.util.List;
import java.util.Queue;

public class MinhaFachada {

	public String checaStatus(TipoEscalonador tipo, int quantum, int tick) {
		return "Escalonador MaisCurtoPrimeiro;Processos: {};Quantum: 0;Tick: " + tick;	}
	
	public String statusFila(TipoEscalonador tipo, Queue<String> fila, int quantum, int tick) {		
		return "Escalonador " + tipo + ";Processos: {Fila: " + fila + "};Quantum: 0;Tick: " + tick;
	}

	public String statusRodando(TipoEscalonador tipo, String rodando, int quantum, int tick) {
		return "Escalonador " + tipo + ";Processos: {Rodando: " + rodando + "};Quantum: 0;Tick: "+ tick;
	
	}
	
	
		public String statusProcessoRodandoFila(TipoEscalonador tipo, String rodando, Queue<String> fila, int quantum, int tick) {		
			return "Escalonador " + tipo + ";Processos: {Rodando: " + rodando + ", Fila: " + fila + "};Quantum: "+ quantum + ";Tick: " + tick;
		}
	}

