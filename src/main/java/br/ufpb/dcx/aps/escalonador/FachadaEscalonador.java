package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FachadaEscalonador {

	private int tick;
	private TipoEscalonador tipo;
	private int quantum;
	private Queue<String> fila = new LinkedList();
	private String rodando;
	private String bloqueado;
	private String finalizado;
	private MinhaFachada status = new MinhaFachada();
	private int controle;
	private int tempo = 0;

	public FachadaEscalonador(TipoEscalonador tipoEscalonador) {
		this.tipo = tipoEscalonador;
		if (tipoEscalonador == null) {
			throw new EscalonadorException();
		}

	}

	public FachadaEscalonador(TipoEscalonador MaisCurtoPrimeiro, int quantum) {

		this.tipo = MaisCurtoPrimeiro;
		this.quantum = quantum;
	}

	public String getStatus() {
		if (rodando == null && fila.size() == 0) {

			return status.checaStatus(tipo, quantum, tick);
		}

		if (rodando == null && fila.size() > 0) {
			return status.statusFila(tipo, fila, quantum, tick);
		}
		if (tick > 0 && fila.size() == 0) {
			return status.statusRodando(tipo, rodando, quantum, tick);
		}

		return status.statusProcessoRodandoFila(tipo, rodando, fila, quantum, tick);
	}

	public void tick() {
		tick++;
		if(fila.contains(finalizado)) {
			this.rodando = finalizado;
			fila.remove(rodando);
		}if(finalizado == null) {
			rodando = bloqueado;
			fila.remove(rodando);
		}
		
			if(tick > controle ) {
				if(fila.size ()> 0) {
					rodando = bloqueado;
					controle += tempo;
					fila.remove(rodando);
					
				}else {
					rodando = null;
				}
			
				
			}
		
		
		
		

		
	}

	public void adicionarProcesso(String nomeProcesso) {
	}

	public void adicionarProcesso(String nomeProcesso, int prioridade) {
	}

	public void finalizarProcesso(String nomeProcesso) {

	}

	public void bloquearProcesso(String nomeProcesso) {
	}

	public void retomarProcesso(String nomeProcesso) {

	}

	public void adicionarProcessoTempoFixo(String string, int duracao) {

		fila.add(string);
		this.controle = duracao;
		if (this.controle > this.tempo) {
			this.tempo = this.controle;
			this.bloqueado = string;
		} else {
			this.finalizado = string;
			controle = duracao;

		}

	}

}
